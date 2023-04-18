package zagvladimir.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageParams {
    String fileExtension;
    Long itemId;
    byte[] imageBytes;
}
