package es.upm.miw.devops.resources;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SystemResource.SYSTEM)
@RequiredArgsConstructor
public class SystemResource {

    public static final String SYSTEM = "/system";
    public static final String VERSION = "/version";

    public static final String VERSION_BADGE = "0.0.1";

    @GetMapping(VERSION)
    public String readVersion() {
        return VERSION_BADGE;
    }
}