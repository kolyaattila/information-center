package com.information.center.authservice.controller;

import com.information.center.authservice.model.MemberDto;
import com.information.center.authservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signIn")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public void create(@RequestBody MemberDto memberDto){

         memberService.create(memberDto);
    }
    @GetMapping("/{externalId}")
    public MemberDto findByName(@PathVariable("externalId")String externalId){
        return memberService.findUserByName(externalId);
    }
    @PutMapping
    public void update(@RequestBody MemberDto memberDto){
         memberService.update(memberDto);
    }




}
