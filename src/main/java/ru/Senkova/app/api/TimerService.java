package ru.Senkova.app.api;

import ru.Senkova.adapter.rest.service.dto.SendEmailFormDto;
import ru.Senkova.domain.HyperlinksUsers;

public interface TimerService {
    public void monitoring(SendEmailFormDto dto);
    void finishMonitoring();
}
