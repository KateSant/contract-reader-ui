package com.thinktalkbuild.contractreader.ui.model.dto;

import lombok.Data;

import java.time.LocalDate;


@Data
public class ContractMetadata {
    String name;
    LocalDate startDate;
}
