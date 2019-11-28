package ru.nachos.core.controller.lib;

import org.springframework.stereotype.Component;
import ru.nachos.core.config.lib.Config;
import ru.nachos.core.fire.lib.Fire;

@Component
public interface Controller extends Runnable{

    InitialPreprocessingData getPreprocessingData();

    Config getConfig();

    Fire getFire();
}
