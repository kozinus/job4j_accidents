package ru.job4j.accidents.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import java.util.Optional;
import java.util.Set;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AccidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccidentService accidentService;

    @MockBean
    private AccidentTypeService typeService;

    @MockBean
    private RuleService ruleService;

    @Test
    @WithMockUser
    public void createPageShouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/createAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("createAccident"));
    }

    @Test
    @WithMockUser
    public void onePageShouldReturn404() throws Exception {
        this.mockMvc.perform(get("/oneAccident").param("id", String.valueOf(0)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("404"));
    }

    @Test
    @WithMockUser
    public void onePageShouldReturnDefaultMessage() throws Exception {
        when(accidentService.findAccidentById(1)).thenReturn(Optional.of(new Accident(1, "name", "text", "address",
                new AccidentType(1, "name"), Set.of(new Rule(1, "Rule")))));
        this.mockMvc.perform(get("/oneAccident").param("id", String.valueOf(1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("oneAccident"));
    }

    @Test
    @WithMockUser
    public void deletePageShouldReturnRedirectMessage() throws Exception {
        this.mockMvc.perform(get("/accidentsDelete/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));
    }

    @Test
    @WithMockUser
    public void savePageShouldReturnRedirectMessage() throws Exception {
        this.mockMvc.perform(post("/saveAccident")
                        .param("rIds", "1")
                        .param("type.id", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));
    }

    @Test
    @WithMockUser
    public void updatePageShouldReturnRedirectMessage() throws Exception {
        when(accidentService.update(any())).thenReturn(true);
        this.mockMvc.perform(post("/updateAccident")
                        .param("rIds", "1")
                        .param("type.id", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));
    }

    @Test
    @WithMockUser
    public void updatePageShouldReturnError() throws Exception {
        when(accidentService.update(any())).thenReturn(false);
        this.mockMvc.perform(post("/updateAccident")
                        .param("rIds", "1")
                        .param("type.id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("404"));
    }
}
