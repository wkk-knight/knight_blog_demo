package com.wkk.kotlincoroutinesdemo.ui

import android.util.Log
import androidx.lifecycle.*
import com.wkk.kotlincoroutinesdemo.api.RetrofitManger
import com.wkk.kotlincoroutinesdemo.entity.Article
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 *
 * Created by wkk on 2019-10-28.
 * @blog https://blog.csdn.net/knight1996
 */
class ArticleViewModel : ViewModel() {

    private val _articleListData = MutableLiveData<List<Article>>()
    //保证外部只能观察此数据，不同通过setValue修改
    val articleListData: LiveData<List<Article>> = _articleListData

    private val _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?> = _errorMsg

    fun fetchArticleList(page: Int) {
        viewModelScope.launch {
            try {
                val result = RetrofitManger.apiService.getArticleList(page)
                _articleListData.value = result.data.datas
            } catch (e: Exception) {
                _errorMsg.value = e.message
            }
        }
    }

    fun fetchArticleList1(page: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitManger.apiService.getArticleList1(page)
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        _articleListData.value = result.data.datas
                    }
                }
            } catch (e: Exception) {
                _errorMsg.value = e.message
            }
        }
    }

    fun  test(){
        RetrofitManger.apiService.getArticleList2(1).execute()
    }

}