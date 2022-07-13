package com.example.fxpro.test.project.model;

import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Quote {
    @Id @NonNull
    Long id;

    @NonNull
    Double price;

    @Indexed @NonNull
    Long instrumentId;

    @Indexed @NonNull
    Long utcTimestamp;
}
