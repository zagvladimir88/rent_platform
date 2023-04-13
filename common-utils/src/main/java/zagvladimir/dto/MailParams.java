package zagvladimir.dto;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailParams {
    private String emailTo;
    private String subject;
    private String pathToBilling;
    Map<String, Object> templateModel = new HashMap<>();
}
