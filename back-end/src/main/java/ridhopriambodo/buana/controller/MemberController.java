package ridhopriambodo.buana.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ridhopriambodo.buana.entity.Member;
import ridhopriambodo.buana.model.*;
import ridhopriambodo.buana.service.MemberService;
import ridhopriambodo.buana.service.UserService;
import ridhopriambodo.buana.service.ValidationService;

import java.io.IOException;
import java.util.List;

@RestController
public class MemberController {

    @Autowired
    private UserService userService;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private MemberService memberService;

    @PostMapping("/api/member/create")
    public Response<String> createMemberData(
            @RequestParam("file") MultipartFile file,
            @ModelAttribute CreateMemberRequest createMemberRequest) {

        validationService.validator(createMemberRequest);

        try {
            String name = createMemberRequest.getName();
            String reportTo = createMemberRequest.getReportTo();
            String posisi = createMemberRequest.getPosition();

            Member member = memberService.createMemberData(file, name, reportTo, posisi);
            return Response.<String>builder().data("OK").build();
        } catch (IOException e) {
            return Response.<String>builder().errors("failed").build();
        }
    }

    @GetMapping("/api/member/picture/{id}")
    public ResponseEntity<?> getProfilePicture(@PathVariable Integer id) throws IOException {

    byte[] imageData=memberService.downloadImageFromFileSystem(id);
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.valueOf("image/png"))
        .body(imageData);
    }



    @GetMapping(path = "/api/member/read",produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<ReadMemberResponse>> readMemberAll() {
        List<ReadMemberResponse> readMemberResponse = memberService.readMemberAllData();
        return Response.<List<ReadMemberResponse>>builder().data(readMemberResponse).build();
    }

    @GetMapping(path = "/api/member/read/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<ReadMemberResponse> readMember(
            @PathVariable("id") Integer id
    ) {
        ReadMemberResponse readMemberResponse = memberService.readMemberData(id);
        return Response.<ReadMemberResponse>builder().data(readMemberResponse).build();
    }

    @PatchMapping(path = "/api/member/update/{id}")
    public Response<String> updateMemberData(
            @PathVariable("id") Integer id,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @ModelAttribute UpdateMemberRequest updateMemberRequest) {

        validationService.validator(updateMemberRequest);

        try {
            String name = updateMemberRequest.getName();
            String reportTo = updateMemberRequest.getReportTo();
            String posisi = updateMemberRequest.getPosition();
            if(file != null){
                Member member = memberService.updateMemberData(id ,file, name, reportTo, posisi );
            }else{
                Member member = memberService.updateWithOutPic(id , name, reportTo, posisi );
            }
            return Response.<String>builder().data("OK").build();
        } catch (IOException e) {
            return Response.<String>builder().errors("failed").build();
        }
    }

    @DeleteMapping(
            path = "/api/member/delete/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<String> deleteMember(
            @PathVariable("id") Integer id
    ) {
        memberService.deleteMember(id);
        return Response.<String>builder().data("Ok").build();
    }
}
