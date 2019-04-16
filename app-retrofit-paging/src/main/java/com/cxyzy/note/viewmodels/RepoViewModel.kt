package com.cxyzy.note.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.cxyzy.note.network.HttpRepository
import com.cxyzy.note.network.bean.Repo
import kotlinx.coroutines.NonCancellable.start
import timber.log.Timber

class RepoViewModel : BaseViewModel() {
    lateinit var repoList: LiveData<PagedList<Repo>>

    fun getRepoDetail(id: String,tryBlock: () -> Unit, catchBlock: (throwable: Throwable) -> Unit, finallyBlock: () -> Unit) {
        launchOnUITryCatch(
                {
                    tryBlock()
                    //TODO: get Repo detail
                },
                {
                    catchBlock(it)
                    Timber.e(it)
                },
                { finallyBlock() },
                true)
    }

    /**
     * @param start 这个方法中可以显示加载进度条等
     * @param finally 可以隐藏进度条等
     */
    fun getRepo(tryBlock: () -> Unit, catchBlock: (throwable: Throwable) -> Unit, finallyBlock: () -> Unit) {
        launchOnUITryCatch(
                {
                    tryBlock()
                    repoList = HttpRepository.getRepo()
                },
                {
                    catchBlock(it)
                    Timber.e(it)
                },
                { finallyBlock() },
                true)
    }
}