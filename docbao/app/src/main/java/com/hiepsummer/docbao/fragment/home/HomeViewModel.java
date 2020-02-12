package com.hiepsummer.docbao.fragment.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }

    /*
    * LiveData là một lớp nắm giữ dữ liệu và cho phép dữ liệu đó có thể quan sát được.
    * Lưu ý : Đảm bảo rằng lưu trữ LiveData object ở trong ViewModel object, tránh lưu trữ ở activities và fragments vì
        - Tránh phình to activities, fragments. Activity và Fragment chỉ đảm bảo nhiệm vụ hiển thị data chứ không lưu trữ data
        - Lưu trữ LiveData objects ở ViewModel đảm bảo LiveData objects tồn tại ngay cả khi có sự thay đổi về config.
    *
    * */
}