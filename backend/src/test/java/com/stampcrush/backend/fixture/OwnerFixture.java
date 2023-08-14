package com.stampcrush.backend.fixture;

import com.stampcrush.backend.entity.user.Owner;

public final class OwnerFixture {
    public static final Owner OWNER1 = new Owner(1L, "이름", "아이디", "비번", "번호");
    public static final Owner OWNER2 = new Owner(2L, "이름", "아이디", "비번", "번호");
    public static final Owner OWNER3 = new Owner(3L, "jena", "jenaId", "jnpw1234", "01098765432");
    public static final Owner GITCHAN = new Owner(4L, "깃짱", "gitchan", "password", "01011112222");
    public static final Owner JENA = new Owner(5L, "제나", "jena", "password", "0101010");

    private OwnerFixture() {
    }
}
