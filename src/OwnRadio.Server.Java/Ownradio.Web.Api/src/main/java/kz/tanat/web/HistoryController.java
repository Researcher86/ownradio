package kz.tanat.web;

import kz.tanat.domain.History;
import kz.tanat.domain.Track;
import kz.tanat.service.HistoryService;
import kz.tanat.util.ResourceFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/histories")
public class HistoryController {
    private final HistoryService historyService;

    @Autowired
    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity save(History history) {
        try {
            historyService.save(history);

            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
