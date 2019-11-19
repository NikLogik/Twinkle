package ru.nachos.core.controller.lib;

import ru.nachos.core.config.lib.Config;
import ru.nachos.core.fire.lib.Fire;

public interface Controller extends Runnable{

    InitialPreprocessingData getPreprocessingData();

    Config getConfig();

    Fire getFire();
}
