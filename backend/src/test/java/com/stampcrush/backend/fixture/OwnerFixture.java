package com.stampcrush.backend.fixture;

import com.stampcrush.backend.entity.user.Owner;

public class OwnerFixture {
    public static final Owner OWNER1 = new Owner("이름", "아이디", "비번", "번호");
    public static final Owner OWNER2 = new Owner("이름", "아이디", "비번", "번호");
    public static final Owner GITCHAN = new Owner("깃짱", "gitchan", "password", "01011112222");
    public static final Owner JENA = new Owner("제나", "jena", "password", "0101010");

    private OwnerFixture() {
    }
}
