package me.ryzeon.bankingsystem.account.interfaces.res.transforms;

import me.ryzeon.bankingsystem.account.domain.model.commands.CreateAccountCommand;
import me.ryzeon.bankingsystem.account.interfaces.res.resources.CreateAccountResource;

public class CreateAccountCommandFromResourceAssembler {

    private CreateAccountCommandFromResourceAssembler() {
    }

    public static CreateAccountCommand toCommandFromResource(CreateAccountResource resource) {
        return new CreateAccountCommand(
                resource.accountNumber(),
                resource.initialBalance(),
                resource.names(),
                resource.lastNames(),
                resource.email(),
                resource.documentNumber()
        );
    }
}
