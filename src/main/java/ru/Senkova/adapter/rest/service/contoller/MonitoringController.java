package ru.Senkova.adapter.rest.service.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.Senkova.adapter.rest.service.dto.InputParametersFormDto;
import ru.Senkova.app.api.ParsingSitesService;
import ru.Senkova.app.api.UserAppService;
import ru.Senkova.domain.UserApp;

@Controller
@RequestMapping(AdminController.BASE_MAPPING)
public class MonitoringController {

    protected static final String BASE_MAPPING = "/Monitoring/application";
    private static final String START_MONITORING = "/start";
    private static final String FINISH_MONITORING = "/finish";
    private static final String DELETE_URLS = "/deleteUrls";

    @Autowired
    private UserAppService userAppService;

    @Autowired
    private ParsingSitesService parsingSitesService;

    @PostMapping(START_MONITORING)
    public ResponseEntity<String> startMonitoring (@RequestBody InputParametersFormDto dto) {
        if(!parsingSitesService.validateUrls(dto.getUrls()))
            return ResponseEntity.badRequest().body("Заполните правильно список ссылок в формате " +
                "'http(s)://домен' или 'домен' пример: 'https://ria.ru/' или 'ria.ru'");
        UserApp userApp=parsingSitesService.findUserAppByLogin(dto.getLogin());
        if(userApp.isConfirmationMail()){
            parsingSitesService.saveHyperlinksUsers(userApp, dto.getUrls(), dto.getKeyWord());
            parsingSitesService.saveLink(dto.getKeyWord(),userApp);
            parsingSitesService.startMonitoring(userApp,dto.getKeyWord());
            return ResponseEntity.ok("Monitoring start!");
        }
        else{
            return ResponseEntity.badRequest().body("Email is not confirmed");
        }
    }

    @PutMapping(FINISH_MONITORING)
    public ResponseEntity<String> finishMonitoring (@RequestBody InputParametersFormDto dto) {
        UserApp userApp=parsingSitesService.findUserAppByLogin(dto.getLogin());
        parsingSitesService.finishMonitoring(userApp,dto.getKeyWord());
        return ResponseEntity.ok("Monitoring finish!");

    }

    @DeleteMapping(DELETE_URLS)
    public ResponseEntity<String> deleteUrls (@RequestBody InputParametersFormDto dto) {
        UserApp userApp=parsingSitesService.findUserAppByLogin(dto.getLogin());
        parsingSitesService.deleteHyperlinksUsers(userApp,dto.getKeyWord());
        return ResponseEntity.ok("Urls delete!");
    }

}

