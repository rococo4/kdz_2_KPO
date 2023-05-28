package kpo.kdz2.controller;

import jakarta.validation.Valid;
import kpo.kdz2.model.DishDTO;
import kpo.kdz2.service.DishService;
import kpo.kdz2.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/dishs")
public class DishController {

    private final DishService dishService;

    public DishController(final DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("dishs", dishService.findAll());
        return "dish/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("dish") final DishDTO dishDTO) {
        return "dish/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("dish") @Valid final DishDTO dishDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "dish/add";
        }
        dishService.create(dishDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("dish.create.success"));
        return "redirect:/dishs";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id, final Model model) {
        model.addAttribute("dish", dishService.get(id));
        return "dish/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Integer id,
            @ModelAttribute("dish") @Valid final DishDTO dishDTO, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "dish/edit";
        }
        dishService.update(id, dishDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("dish.update.success"));
        return "redirect:/dishs";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Integer id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = dishService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            dishService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("dish.delete.success"));
        }
        return "redirect:/dishs";
    }

}
