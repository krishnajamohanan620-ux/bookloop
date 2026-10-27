package com.campus.bookexchange.controller;

import com.campus.bookexchange.model.Book;
import com.campus.bookexchange.service.BookService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Controller
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String home(@RequestParam(required = false) String title,
                       @RequestParam(required = false) String course,
                       @RequestParam(required = false) Integer semester,
                       Model model) {
        model.addAttribute("books", bookService.search(title, course, semester));
        model.addAttribute("title", title == null ? "" : title);
        model.addAttribute("selectedCourse", course == null ? "" : course);
        model.addAttribute("selectedSemester", semester == null ? "" : semester);
        return "index";
    }

    @GetMapping("/sell")
    public String showSellForm(Model model) {
        model.addAttribute("book", new Book());
        return "sell-book";
    }

    @PostMapping("/sell")
    public String sellBook(@Valid @ModelAttribute("book") Book book,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "sell-book";
        }
        bookService.save(book);
        redirectAttributes.addFlashAttribute("successMessage", "Your book listing was published!");
        return "redirect:/";
    }

    @PostMapping("/cart/add/{id}")
    public String addToCart(@PathVariable Long id, HttpSession session,
                            RedirectAttributes redirectAttributes) {
        Book book = bookService.findById(id);
        if (book == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "That book could not be found.");
            return "redirect:/";
        }
        @SuppressWarnings("unchecked")
        Set<Long> cart = (Set<Long>) session.getAttribute("cart");
        if (cart == null) cart = new LinkedHashSet<>();
        cart.add(id);
        session.setAttribute("cart", cart);
        redirectAttributes.addFlashAttribute("successMessage", "Book added to your selected books.");
        return "redirect:/";
    }

    @GetMapping("/cart")
    public String showCart(HttpSession session, Model model) {
        @SuppressWarnings("unchecked")
        Set<Long> cartIds = (Set<Long>) session.getAttribute("cart");
        List<Book> cartBooks = new ArrayList<>();
        if (cartIds != null) {
            for (Long id : cartIds) {
                Book book = bookService.findById(id);
                if (book != null) cartBooks.add(book);
            }
        }
        model.addAttribute("cartBooks", cartBooks);
        return "cart";
    }

    @PostMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable Long id, HttpSession session) {
        @SuppressWarnings("unchecked")
        Set<Long> cart = (Set<Long>) session.getAttribute("cart");
        if (cart != null) {
            cart.remove(id);
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart";
    }
}
