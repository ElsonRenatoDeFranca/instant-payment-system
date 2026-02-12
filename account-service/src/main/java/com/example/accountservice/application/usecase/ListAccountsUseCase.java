package com.example.accountservice.application.usecase;

import com.example.common.dto.ContaDTO;
import java.util.List;

public interface ListAccountsUseCase {
    List<ContaDTO> execute();
}
