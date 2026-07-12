package com.kritika.spring_boot_url_shortener.web.controllers;

import com.kritika.spring_boot_url_shortener.ApplicationProperties;
import com.kritika.spring_boot_url_shortener.domain.exceptions.ShortUrlNotFoundException;
import com.kritika.spring_boot_url_shortener.domain.models.*;
import com.kritika.spring_boot_url_shortener.domain.services.ShortUrlService;
import com.kritika.spring_boot_url_shortener.domain.services.UserService;
import com.kritika.spring_boot_url_shortener.web.dtos.CreateShortUrlForm;
import com.kritika.spring_boot_url_shortener.web.dtos.RegisterUserRequest;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    private final ShortUrlService shortUrlService;
    private final UserService userService;

    private final ApplicationProperties properties;
    private final SecurityUtils securityUtils;

    public HomeController(
            ShortUrlService shortUrlService,
            UserService userService,
            ApplicationProperties properties,
            SecurityUtils securityUtils) {

        this.shortUrlService = shortUrlService;
        this.userService = userService;
        this.properties = properties;
        this.securityUtils = securityUtils;
    }

    @GetMapping("/")
    public String home(
            @RequestParam(defaultValue = "1") Integer page,
            Model model) {

        addShortUrlsDataToModel(model, page);

        model.addAttribute(
                "totalUrls",
                shortUrlService.getTotalUrls()
        );

        model.addAttribute(
                "totalClicks",
                shortUrlService.getTotalClicks()
        );

        model.addAttribute(
                "topUrls",
                shortUrlService.getTopClickedUrls()
        );

        model.addAttribute(
                "createShortUrlForm",
                new CreateShortUrlForm(
                        "",
                        false,
                        null
                )
        );

        return "index";
    }

    private void addShortUrlsDataToModel(
            Model model,
            int pageNo) {

        PagedResult<ShortUrlDto> shortUrls =
                shortUrlService.findAllPublicShortUrls(
                        pageNo,
                        properties.pageSize()
                );

        model.addAttribute(
                "shortUrls",
                shortUrls
        );

        model.addAttribute(
                "baseUrl",
                properties.baseUrl()
        );

        model.addAttribute(
                "paginationUrl",
                "/"
        );
    }

    @PostMapping("/short-urls")
    public String createShortUrl(
            @ModelAttribute("createShortUrlForm")
            @Valid CreateShortUrlForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            addShortUrlsDataToModel(model, 1);
            return "index";
        }

        try {

            Long userId =
                    securityUtils.getCurrentUserId();

            CreateShortUrlCmd cmd =
                    new CreateShortUrlCmd(
                            form.originalUrl(),
                            form.isPrivate(),
                            form.expirationInDays(),
                            userId
                    );

            ShortUrlDto shortUrlDto =
                    shortUrlService.createShortUrl(cmd);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Short URL created successfully: "
                            + properties.baseUrl()
                            + "/s/"
                            + shortUrlDto.shortKey()
            );

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Failed to create short URL"
            );
        }

        return "redirect:/";
    }

    @GetMapping("/s/{shortKey}")
    public String redirectToOriginalUrl(
            @PathVariable String shortKey) {

        Long userId =
                securityUtils.getCurrentUserId();

        Optional<ShortUrlDto> shortUrlDtoOptional =
                shortUrlService.accessShortUrl(
                        shortKey,
                        userId
                );

        if (shortUrlDtoOptional.isEmpty()) {
            throw new ShortUrlNotFoundException(
                    "Invalid short key: "
                            + shortKey
            );
        }

        ShortUrlDto shortUrlDto =
                shortUrlDtoOptional.get();

        return "redirect:"
                + shortUrlDto.originalUrl();
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(
            Model model) {

        model.addAttribute(
                "registerUserRequest",
                new RegisterUserRequest(
                        "",
                        "",
                        ""
                )
        );

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute("registerUserRequest")
            @Valid RegisterUserRequest request,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {

            CreateUserCmd cmd =
                    new CreateUserCmd(
                            request.email(),
                            request.password(),
                            request.name(),
                            Role.ROLE_USER
                    );

            userService.createUser(cmd);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Registration successful. Please login."
            );

            return "redirect:/login";

        } catch (Exception e) {

            model.addAttribute(
                    "errorMessage",
                    e.getMessage()
            );

            model.addAttribute(
                    "registerUserRequest",
                    request
            );

            return "register";
        }
    }

    @GetMapping("/my-urls")
    public String showUserUrls(
            @RequestParam(defaultValue = "1")
            int page,
            Model model) {

        Long currentUserId =
                securityUtils.getCurrentUserId();

        PagedResult<ShortUrlDto> myUrls =
                shortUrlService.getUserShortUrls(
                        currentUserId,
                        page,
                        properties.pageSize()
                );

        model.addAttribute(
                "shortUrls",
                myUrls
        );

        model.addAttribute(
                "baseUrl",
                properties.baseUrl()
        );

        model.addAttribute(
                "paginationUrl",
                "/my-urls"
        );

        return "my-urls";
    }

    @PostMapping("/delete-urls")
    public String deleteUrls(
            @RequestParam(
                    value = "ids",
                    required = false
            ) List<Long> ids,
            RedirectAttributes redirectAttributes) {

        if (ids == null || ids.isEmpty()) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "No URLs selected for deletion"
            );

            return "redirect:/my-urls";
        }

        try {

            Long currentUserId =
                    securityUtils.getCurrentUserId();

            shortUrlService.deleteUserShortUrls(
                    ids,
                    currentUserId
            );

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Selected URLs deleted successfully"
            );

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Error deleting URLs: "
                            + e.getMessage()
            );
        }

        return "redirect:/my-urls";
    }


}