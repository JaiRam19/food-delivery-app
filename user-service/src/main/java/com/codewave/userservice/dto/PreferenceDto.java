package com.codewave.userservice.dto;

import com.codewave.userservice.entity.User;
import com.codewave.userservice.entity.UserPreferences;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PreferenceDto {

    @Builder.Default
    private Boolean notifications = true;
    @Builder.Default
    private String theme = "light";
    @Builder.Default
    private String appLanguage = "english";
}
