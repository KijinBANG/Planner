package kr.co.team.planner.controller;

import kr.co.team.planner.service.JoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/join")
public class JoinController {
//    private final JoinService joinService;

    @GetMapping("/list")
    public void main(Model model){

        //일단 아래와 같이 직접 입력한 데이터로 연습하고!
        //완전히 이해하고 난 이후에 DB를 활용하여 연동시키며 데이터를 다루는 공부를 이어나가자!
        Map<String, Object> map = new HashMap<>();
        map.put("Language", "Java");
        map.put("IDE", "IntelliJ");
        map.put("BuildTool", "Gradle");
        map.put("WAS", "Tomcat");
        model.addAttribute("map", map);

        List<String> task = new ArrayList<>();
        task.add("Back End Developer");
        task.add("Front End Developer");
        task.add("Full Stack Developer");
        task.add("Database Engineer");
        task.add("Operator");
        task.add("Big Data Processing");
        task.add("Big Data Infra Builder");
        task.add("Cloud Infra Builder");
        task.add("DevOps");
        task.add("AI");
        task.add("MLOps");
        model.addAttribute("list", task);
    }
}
