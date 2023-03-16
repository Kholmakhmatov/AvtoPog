package uz.agrobank.avtopog.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchDto {

   private String name;

   public SearchDto(String name) {
      this.name = name;
   }
}
