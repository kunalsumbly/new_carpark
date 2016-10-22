package com.au.sofico.ssa_carpark.repo;

import java.util.List;

import com.au.sofico.ssa_carpark.domain.Member;

public interface MemberDao
{
    public Member findById(Long id);

    public Member findByEmail(String email);

    public List<Member> findAllOrderedByName();

    public void register(Member member);
}
