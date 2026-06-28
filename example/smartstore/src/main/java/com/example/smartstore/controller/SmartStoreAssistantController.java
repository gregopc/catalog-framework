package com.example.smartstore.controller;

import com.catalog.framework.controller.AssistantController;
import com.catalog.framework.service.AssistantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assistant")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SmartStoreAssistantController extends AssistantController {

    private final AssistantService assistantService;

    @Override
    protected AssistantService getService() {
        return assistantService;
    }
}