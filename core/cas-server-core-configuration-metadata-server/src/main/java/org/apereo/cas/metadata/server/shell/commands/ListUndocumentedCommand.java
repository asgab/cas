package org.apereo.cas.metadata.server.shell.commands;

import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.metadata.CasConfigurationMetadataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * This is {@link ListUndocumentedCommand}.
 *
 * @author Misagh Moayyed
 * @since 5.2.0
 */
@Service
public class ListUndocumentedCommand implements CommandMarker {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListUndocumentedCommand.class);

    /**
     * List undocumented settings.
     */
    @CliCommand(value = "list", help = "List all CAS undocumented properties.")
    public void list() {
        final CasConfigurationMetadataRepository repository = new CasConfigurationMetadataRepository();
        repository.getRepository().getAllProperties()
                .entrySet()
                .stream()
                .filter(p -> p.getKey().startsWith("cas.")
                        && (StringUtils.isBlank(p.getValue().getShortDescription()) || StringUtils.isBlank(p.getValue().getDescription())))
                .map(Map.Entry::getValue)
                .forEach(p -> {
                    LOGGER.info("Property: {}", p.getId());
                });
    }
}
