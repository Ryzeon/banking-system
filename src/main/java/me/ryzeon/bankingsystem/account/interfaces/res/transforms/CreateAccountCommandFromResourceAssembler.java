package me.ryzeon.bankingsystem.account.interfaces.res.transforms;

import lombok.experimental.UtilityClass;
import me.ryzeon.bankingsystem.account.domain.model.commands.CreateAccountCommand;
import me.ryzeon.bankingsystem.account.interfaces.res.resources.CreateAccountResource;

@UtilityClass
public class CreateAccountCommandFromResourceAssembler {

    public CreateAccountCommand toCommandFromResource(CreateAccountResource resource) {
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
