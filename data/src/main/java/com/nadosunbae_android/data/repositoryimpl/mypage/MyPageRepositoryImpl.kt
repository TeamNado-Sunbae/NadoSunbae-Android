package com.nadosunbae_android.data.repositoryimpl.mypage

import com.nadosunbae_android.data.datasource.remote.mypage.MyPageDataSource
import com.nadosunbae_android.data.mapper.mypage.MypageMapper
import com.nadosunbae_android.domain.model.main.AppLinkData
import com.nadosunbae_android.domain.model.mypage.*
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor(private val dataSource: MyPageDataSource) : MyPageRepository {
    override suspend fun putMyPageModify(myPageModifyItem: MyPageModifyItem): MyPageModifyData {
        return MypageMapper.mapperToModifyData(dataSource.putMyPageModify(
            MypageMapper.mapperToModifyItem(myPageModifyItem)
        ))
    }

    override suspend fun getMyPageVersion(): MyPageVersionData {
        return MypageMapper.mapperToVersion(dataSource.getMyPageVersion())
    }

    override suspend fun postMyPageLogOut(): MyPageLogOutData {
        return MypageMapper.mapperToLogOut(dataSource.postMyPageLogOut())
    }

    override suspend fun getMyPageBlock(): MyPageBlockData {
        return MypageMapper.mapperToBlock(dataSource.getMyPageBlock())
    }

    override suspend fun postMyPageBlockUpdate(myPageBlockUpdateItem: MyPageBlockUpdateItem): MyPageBlockUpdateData {
        return MypageMapper.mapperToBlockUpdateData(dataSource.postMyPageBlockUpdate(
            MypageMapper.mapperToBlockUpdateItem(myPageBlockUpdateItem)
        ))
    }

    override suspend fun postMyPageResetPassword(myPageResetPasswordItem: MyPageResetPasswordItem): MyPageResetPasswordData {
        return MypageMapper.mapperToResetPasswordData(dataSource.postResetPassword(
            MypageMapper.mapperToResetPasswordItem(myPageResetPasswordItem)
        ))
    }

    override suspend fun deleteMyPageQuit(myPageQuitItem: MyPageQuitItem): MyPageQuitData {
        return MypageMapper.mapperToQuitData(dataSource.deleteQuit(
            MypageMapper.mapperToQuitItem(myPageQuitItem)
        ))
    }

}