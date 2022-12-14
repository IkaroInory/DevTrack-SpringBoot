package cn.auroralab.devtrack.service;

import cn.auroralab.devtrack.dto.MemberDTO;
import cn.auroralab.devtrack.exception.system.PermissionDeniedException;
import cn.auroralab.devtrack.exception.system.RecordNotFoundException;
import cn.auroralab.devtrack.exception.system.RequiredParametersIsEmptyException;
import cn.auroralab.devtrack.po.Member;
import cn.auroralab.devtrack.util.PageInformation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface MemberService extends IService<Member> {
    /**
     * 插入一系列默认的用户-项目映射。
     *
     * @param requesterUUID 请求人UUID。
     * @param projectUUID   项目UUID。
     * @param usernameList  用户名列表
     * @return 成功添加的行数。
     * @author Guanyu Hu
     * @since 2022-12-04
     */
    int newDefaultRecords(String requesterUUID, String projectUUID, List<String> usernameList)
            throws RequiredParametersIsEmptyException;

    /**
     * 插入一条用户-项目映射。
     *
     * @param userUUID    用户uuid。
     * @param projectUUID 项目uuid。
     * @author Guanyu Hu
     * @since 2022-11-10
     */
    void newRecord(String userUUID, String projectUUID, String roleUUID);

    /**
     * 修改成员角色。
     *
     * @param requesterUUID 请求人UUID。
     * @param recordUUID    项目-用户映射记录UUID。
     * @param roleUUID      角色UUID。
     * @author Guanyu Hu
     * @since 2022-12-01
     */
    void updateMemberRole(String requesterUUID, String recordUUID, String roleUUID)
            throws RequiredParametersIsEmptyException, RecordNotFoundException, PermissionDeniedException;

    /**
     * 获取项目成员信息。
     *
     * @param projectUUID 项目UUID。
     * @return 成员信息列表。
     * @author Guanyu Hu
     * @since 2022-11-18
     */
    List<MemberDTO> getMemberList(String projectUUID)
            throws RequiredParametersIsEmptyException;

    /**
     * 移除项目成员。
     *
     * @param requestUUID    请求人UUID。
     * @param recordUUIDList 映射记录UUID列表。
     * @author Guanyu Hu
     * @since 2022-12-07
     */
    void removeMembers(String requestUUID, List<String> recordUUIDList)
            throws RequiredParametersIsEmptyException, PermissionDeniedException;

    /**
     * 获取一页项目成员信息。
     *
     * @param projectUUID 项目UUID。
     * @param pageNum     页码。
     * @param pageSize    每页大小。
     * @author Guanyu Hu
     * @since 2022-11-20
     */
    PageInformation<MemberDTO> getMemberList(String projectUUID, int pageNum, int pageSize)
            throws RequiredParametersIsEmptyException;
}
