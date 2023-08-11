package com.stampcrush.backend.application;

import com.stampcrush.backend.common.KorNamingConverter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@KorNamingConverter
@ExtendWith(MockitoExtension.class)
public @interface ServiceSliceTest {
}
