package edu.spv.numbergenerateservice.service.impl;

import edu.spv.numbergenerateservice.service.RedisService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NumberGenerateServiceImplTest {

    @Mock
    private RedisService redisService;

    @InjectMocks
    private NumberGenerateServiceImpl numberGenerateService;

    @Nested
    class GenerateUniqueNumber {
        @Test
        void checkGenerateUniqueNumberShouldGenerateNewNumberIfGeneratedNumberExists() {
            when(redisService.saveIfAbsent(anyString()))
                    .thenReturn(false)
                    .thenReturn(false)
                    .thenReturn(true);

            numberGenerateService.generateUniqueNumber();

            verify(redisService, times(3)).saveIfAbsent(anyString());
        }

        @Test
        void checkGenerateUniqueNumberShouldGenerateNotBlankString() {
            when(redisService.saveIfAbsent(anyString()))
                    .thenReturn(true);

            String result = numberGenerateService.generateUniqueNumber();

            assertThat(result).isNotBlank();
        }

        @Test
        void checkGenerateUniqueNumberShouldReturnStringWithSize13() {
            when(redisService.saveIfAbsent(anyString()))
                    .thenReturn(true);

            String result = numberGenerateService.generateUniqueNumber();

            assertThat(result).hasSize(13);
        }

        @Test
        void checkGenerateUniqueNumberShouldReturnNumberInValidFormat() {
            when(redisService.saveIfAbsent(anyString())).thenReturn(true);

            String regex = "^\\d{13}$";
            String result = numberGenerateService.generateUniqueNumber();

            assertThat(result).matches(regex);
        }

        @Test
        void checkGenerateUniqueNumberShouldContainValidDate() {
            when(redisService.saveIfAbsent(anyString())).thenReturn(true);

            String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String result = numberGenerateService.generateUniqueNumber();
            String datePart = result.substring(5, 13);

            assertThat(datePart).isEqualTo(currentDate);
        }
    }
}
