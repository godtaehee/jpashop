package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
// Final가지고 생성자만들어주는
// 나머지 필드를 쓸수도 있어서 Final로 해놓은 저거만 생성자에 사용하고싶다면 쓰면된다.
@RequiredArgsConstructor
//  모든 생성자 다만들어줌
//@AllArgsConstructor
public class MemberService {

    // Autowired의 장점
    // 필드 접근이 불가능하다.
    private final MemberRepository memberRepository;

    // Test할때 Mock 객체를  집어넣을수 있다.
    // 보통 Injection은 애플리케이션이 동작하고나서는 절대 다시 할일이 없어서 다른데에서 해당 setRepository를 또 사용할수가 있음
    //    @Autowired
    //    public void setMemberRepository(MemberRepository memberRepository) {
    //        this.memberRepository = memberRepository;
    //    }

    @Transactional
    public Long join(Member member) {
        validateDuplicatedMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicatedMember(Member member) {

        List<Member> findMembers = memberRepository.findByName(member.getName());
        // 회원 이름를 Unique로 하는걸 권장한다.
        // 왜냐하면 WAS가 여러개 돌아가고 그 WAS에서 싹다 밑에 If문을 통과할수가 있기때문이다.
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findMember(Long id) {
        return memberRepository.findOne(id);
    }
}
