package ru.gb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

  @GetMapping("/table")
  public String table(Model model) {
    List<Person> people = List.of(
      new Person("John", "Googler", 24, "google.com"),
      new Person("Alex", "Smith", 32, "jetbrains.com"),
      new Person("Igor", "Chestnov", 100, "amazon.com")
    );
    model.addAttribute("people", people);
    return "table";
  }

  @GetMapping("/list")
  public String list(@RequestParam int count, Model model) {
    List<Item> items = new ArrayList<>();
    for (int i = 1; i <= count; i++) {
      items.add(new Item(i, "Item #" + i));
    }

    model.addAttribute("items", items);
    return "list";
  }

  @GetMapping("/home")
  public String home(@RequestParam(required = false) String name, @RequestParam(required = false) String color, Model model) {
    if (name != null) {
      model.addAttribute("name", name);
    } else {
      model.addAttribute("name", "world");
    }
    model.addAttribute("myColor", (color == null ? "black" : color));
    return "home";
  }

}
