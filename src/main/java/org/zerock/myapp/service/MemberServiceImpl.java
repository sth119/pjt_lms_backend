package org.zerock.myapp.service;

import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@Service
public class MemberServiceImpl implements MemberService {
    /*
	private PasswordEncoder passwordEncoder;
    @Autowired MemberRepository dao;
	
	
	@PostConstruct
    void postConstruct(){
        log.debug("MemberServiceImpl -- postConstruct() invoked");
        log.debug("dao: {}", dao);
    }//postConstruct


	@Override
	public List<Member> getAllList() {	//검색 없는 전체 리스트
		log.debug("MemberServiceImpl -- getAllList() invoked");
		
		List<Member> list = dao.findAll();
		
		return list;
	} // getAllList
	
	@Override
	public List<Member> getSearchList(MemberDTO dto) {	//검색 있는 전체 리스트
		log.debug("MemberServiceImpl -- getSearchList(()) invoked", dto);

		List<Member> list = new Vector<>();
		log.debug("리포지토리 미 생성");
		
		return list;
	} // getSearchList
	
	@Override
	public Member create(MemberDTO dto) {	//등록 처리
		log.debug("MemberServiceImpl -- create({}) invoked", dto);
		
		Member data = new Member();//dao.save(dto);
		log.debug("create data: {}", data);
		
		return data;
	} // create
	
	@Override
	public Member getById(String id) {	// 단일 조회
		log.debug("MemberServiceImpl -- getById({}) invoked", id);
		
		//값이 존재하면 반환하고, 없으면 new Course()와 같은 기본값을 반환합니다.
		Member data = new Member();//dao.findById(id).orElse(new Member());
		
		return data;
	} // getById
	
	@Override
	public Boolean update(MemberDTO dto) {//수정 처리
		log.debug("MemberServiceImpl -- update({}) invoked", dto);
		
//		Member data = dao.save(dto);
//		log.debug("create data: {}", data);
		Boolean isUpdate = true;
		return isUpdate;
	} // update

	@Override
	public Boolean deleteById(String id) { // 삭제 처리
		log.debug("MemberServiceImpl -- deleteById({}) invoked", id);
		
		//dao.deleteById(id);
		return true;
	} // deleteById
	
  // ================= 로그인 로직 =======================
    
    public Optional<Member> findByMemberId(String memberId) {
    	return this.dao.findByMemberId(memberId);
    }  // 사용자의 id 가 db 에 저장이 되어 있는지 검색

    public Boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    } // 비번이 일치하는지 검증.

    public Optional<Member> login(String memberId, String password) {
    	Optional<Member> memberOptional = this.dao.findByMemberId(memberId);
    	
    	if (memberOptional.isEmpty()) {
    		throw new IllegalArgumentException("아이디 또는 패스워드를 입력해주세요.");
    	} // 아이디 & 비밀번호 창이 비어있는 경우 검증
    	
    	Member member = memberOptional.get();
    	
    	if (!checkPassword(password, member.getMemberPassword())) {
    		throw new IllegalArgumentException("아이디 또는 패스워드가 틀립니다.");
    	} // 비밀번호 검증.
    	
    	if (member.getMemberTypeCode() != MemberType.MANAGER) {
    		throw new IllegalArgumentException("현재 관리자만 로그인 가능합니다.");
    	} // 관리자만 로그인 가능 
    	
    	return memberOptional;
    } // end Optional<MemberEntity> login
    

    // ================= 회원가입 로직 =======================


    public void registerMember(MemberDTO dto) {
    	Member member = new Member();
    	
    	
    	member.setMemberId(dto.getMemberId()); // 유저 아이디. 
    	member.setMemberPassword(dto.getMemberPassword()); // 비밀번호.
    	member.setMemberPassword(passwordEncoder.encode(member.getMemberPassword()));  // 비밀번호 암호화 저장
    	
    	member.setMemberName(dto.getMemberName()); // 유저 닉네임 
    	member.setMemberPhone(dto.getMemberPhone()); // 전화번호
    	member.setRequestCrsCode(dto.getRequestCrsCode()); // 코스.
    	member.setStudentImage(dto.getStudentImage()); // 사진.
    	member.setCrtDate(new Date()); // 생성날짜.
    	
   
    	member.setMemberType(dto.getMemberType()); // 유저타입. (관리자/강사/훈련생)
    	member.setMemberTypeCode(MemberType.fromCode(dto.getMemberType())); // 로그인시 관리자만 로그인 되도록 비교.
    	
    	this.dao.save(member); // db에 저장.
    } // 회원가입 로직. 

    
    public String checkIdDuplicate(String memberId) {
    	boolean isDuplicate = this.dao.existsByMemberId(memberId);
    	
    	if (isDuplicate) {
    		return "이미 사용 중인 아이디입니다";
    	} else  {
    		return "사용 가능한 아이디 입니다.";
    	}
    	
    } // 아이디 중복체크 로직 
       
    // ===================================================
	
	
	
	
	*/
}//end class
