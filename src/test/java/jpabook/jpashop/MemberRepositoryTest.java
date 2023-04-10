package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    void testSave() {

        // given
        Member member = new Member("Member1");

        // when
        Long id = memberRepository.save(member);
        Member findMember = memberRepository.find(id);

        // then
        Assertions.assertEquals(member, findMember);
    }


}
