package webemex.eshop.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class IndexController {
    @GetMapping("/")
    public Map<String, Object> index() {
        Map<String, Object> response = new HashMap<>();

        response.put("userLogged", true);

        return response;
    }
}