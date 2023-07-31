package com.stampcrush.backend.fixture;

import com.stampcrush.backend.entity.user.Owner;

public class OwnerFixture {
    public static final Owner OWNER1 = new Owner("이름", "아이디", "비번", "번호");
    public static final Owner OWNER2 = new Owner("이름", "아이디", "비번", "번호");
    public static final Owner OWNER3 = new Owner("jena", "jenaId", "jnpw1234", "01098765432");

    private OwnerFixture() {
    }
}
