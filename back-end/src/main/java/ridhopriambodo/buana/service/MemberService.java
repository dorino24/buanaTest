package ridhopriambodo.buana.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ridhopriambodo.buana.entity.Member;
import ridhopriambodo.buana.entity.User;
import ridhopriambodo.buana.model.CreateMemberRequest;
import ridhopriambodo.buana.model.ReadMemberResponse;
import ridhopriambodo.buana.model.UserResponse;
import ridhopriambodo.buana.repository.MemberRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public void create(CreateMemberRequest request){
        validationService.validator(request);

    }

    private String uploadDir ="D://IT/Java/buana/picture/" ;

    public Member findMember(Integer id){
        Member member = memberRepository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Wrong ID"));;
        return member;
    }


    @Transactional
    public Member createMemberData( MultipartFile file, String name, String repostTo, String posisi) throws IOException {

        String pictureDB=UUID.randomUUID()+"_"+file.getOriginalFilename();
        String filePath=uploadDir+pictureDB;

        System.err.println(filePath);
        file.transferTo(new File(filePath));

        Member member = new Member();
        member.setName(name);
        member.setPosition(posisi);
        member.setReportTo(repostTo);
        member.setPicture(pictureDB);

        return memberRepository.save(member);
    }


    public ReadMemberResponse readMemberData(Integer id){
        Member member = findMember(id);
        return ReadMemberResponse.builder()
                .name(member.getName())
                .position(member.getPosition())
                .reportTo(member.getReportTo())
                .picture(member.getPicture())
                .build();
    }

    public List<ReadMemberResponse> readMemberAllData(){
        List<Member> members = memberRepository.findAll();
        return  members.stream()
                .map(member -> ReadMemberResponse.builder()
                        .id(member.getId())
                        .name(member.getName())
                        .position(member.getPosition())
                        .reportTo(member.getReportTo())
                        .picture(member.getPicture())
                        .build())
                .collect(Collectors.toList());
    }


    @Transactional
    public byte[] downloadImageFromFileSystem(Integer id) throws IOException {
        Member member = findMember(id);

        String filePath=uploadDir+ member.getPicture();

        if(member.getPicture() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Picture Not Found");
        }
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }


    @Transactional
    public Member updateWithOutPic(Integer id, String name, String repostTo, String posisi) throws IOException {

        Member member = findMember(id);

        member.setName(name);
        member.setPosition(posisi);
        member.setReportTo(repostTo);

        return memberRepository.save(member);
    }

    @Transactional
    public Member updateMemberData(Integer id, MultipartFile file, String name, String repostTo, String posisi) throws IOException {

        Member member = findMember(id);
        String oldFilePath=uploadDir+ member.getPicture();
        File oldFile = new File(oldFilePath);

        if(oldFile.exists()){
            oldFile.delete();
        }

        String pictureDB=UUID.randomUUID()+"_"+file.getOriginalFilename();
        String filePath=uploadDir+pictureDB;

        file.transferTo(new File(filePath));

        member.setName(name);
        member.setPosition(posisi);
        member.setReportTo(repostTo);
        member.setPicture(pictureDB);

        return memberRepository.save(member);
    }

    @Transactional
    public void deleteMember(Integer id){

        if(memberRepository.existsById(id)){
            Member member = memberRepository.findById(id)
                    .orElseThrow( () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Picture Not Found"));
            String filePath=uploadDir+ member.getPicture();

            File file = new File(filePath);

            if(file.exists()){
                file.delete();
            }

            try{
                memberRepository.deleteById(id);
            }catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Failed to delete ID");
            }
        }else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"ID Not Found");
        }
    }

}
