package ru.itis.delivery.services.apiCurrency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeaderCurrency {

    String name;

    String value;

}
