package me.ryzeon.bankingsystem.account.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record AccountInformation(
        String names,
        String lastNames,
        String email,
        String documentNumber
) {
    public AccountInformation() {
        this("", "", "", "");
    }
}
