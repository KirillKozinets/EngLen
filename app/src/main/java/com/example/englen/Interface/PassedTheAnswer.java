
//Интерфейс для взаимодействия между фрагментами во время прохождения теста

package com.example.englen.Interface;

import android.support.annotation.Nullable;

public interface PassedTheAnswer {
    void PassedTheAnswer(@Nullable boolean trueAnswer , int progress);
    void Exit();
}
