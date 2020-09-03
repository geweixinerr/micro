package micro.commons.util;

import static org.apache.commons.lang3.StringUtils.*;

import java.util.function.Supplier;

import org.apache.commons.collections.CollectionUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import micro.commons.annotation.ThreadSafe;
import micro.commons.exception.BusinessRuntimeException;
import micro.commons.page.PageParameter;
import micro.commons.page.Pages;

/**
 * 分页工具类,基于PageHelper
 * 
 * @author gewx
 **/
@ThreadSafe
public final class PageHelperUtils {

	private static int ZERO = 0;

	/**
	 * 通用分页方法
	 * 
	 * @author gewx
	 * @param parameter  分页参数
	 * @param pageResult 分页数据集
	 * @return void
	 **/
	public static <T> Pages<T> limit(PageParameter parameter, Supplier<Page<T>> pageResult) {
		if (parameter.getStartpage() == null) {
			throw new BusinessRuntimeException("页码必填");
		}

		if (parameter.getPagesize() == null) {
			throw new BusinessRuntimeException("页行必填");
		}

		if (parameter.getPagesize().intValue() == ZERO) {
			throw new BusinessRuntimeException("页行必须大于0");
		}

		PageHelper.startPage(parameter.getStartpage(), parameter.getPagesize(), false);
		if (isNotBlank(parameter.getSortname())) {
			PageHelper.orderBy(parameter.getSortname());
			if (parameter.isSymbol()) {
				PageHelper.orderBy(parameter.getSortname() + " DESC");
			}
		}

		Page<T> page = pageResult.get();
		Pages<T> pages = new Pages<T>();
		pages.setTotalPageNum(page.getPages());
		pages.setTotalNum(page.getTotal());
		pages.setPageNum(page.getPageNum());
		pages.setPageSize(page.getPageSize());
		pages.setPages(page.getResult());
		return pages;
	}

	/**
	 * 判断查询数据是否存在
	 * 
	 * @author gewx
	 * @param pageResult 分页数据集
	 * @return boolean
	 **/
	public static <T> boolean isExists(Supplier<Page<T>> pageResult) {
		PageParameter parameter = new PageParameter();
		parameter.setStartpage(1);
		parameter.setPagesize(1);

		PageHelper.startPage(parameter.getStartpage(), parameter.getPagesize(), false);

		return CollectionUtils.isNotEmpty(pageResult.get());
	}
}
