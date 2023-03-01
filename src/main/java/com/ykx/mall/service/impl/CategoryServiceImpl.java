package com.ykx.mall.service.impl;

import com.ykx.mall.dao.CategoryMapper;
import com.ykx.mall.pojo.Category;
import com.ykx.mall.service.ICategoryService;
import com.ykx.mall.vo.CategoryVo;
import com.ykx.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static com.ykx.mall.consts.MallConst.ROOT_PARENT_ID;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 查询所有商品目录及其子类
     *
     */
    @Override
    public ResponseVo<List<CategoryVo>> selectAll() {
        List<CategoryVo> categoryVoList = new ArrayList<>();
        List<Category> categories = categoryMapper.selectAll();

        //查出parent_id=0
        for (Category category : categories
        ) {
            if (category.getParentId().equals(ROOT_PARENT_ID)) {
                CategoryVo categoryVo = new CategoryVo();
                //spring对象拷贝，将category拷贝给categoryVo
                BeanUtils.copyProperties(category, categoryVo);
                categoryVoList.add(categoryVo);
                categoryVoList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());
            }
        }
        //查询子目录
        findsubCategories(categoryVoList, categories);
        return ResponseVo.success(categoryVoList);
    }

    //查询子目录方法的实现
    private void findsubCategories(List<CategoryVo> categoryVoList, List<Category> categories) {
        for (CategoryVo categoryVo : categoryVoList) {
            List<CategoryVo> subCategoryVoList = new ArrayList<>();
            for (Category category : categories) {
                //对比父目录id与子目录的父id,查到设置subCategory,直到null
                if (categoryVo.getId().equals(category.getParentId())) {
                    CategoryVo subCategoryVo = new CategoryVo();
                    //spring对象拷贝，将category拷贝给categoryVo
                    BeanUtils.copyProperties(category, subCategoryVo);
                    subCategoryVoList.add(subCategoryVo);
                }
                //sort(Comparator.comparing(CategoryVo::getSortOrder).reversed()); 排序，反转
                subCategoryVoList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());
                categoryVo.setSubCategories(subCategoryVoList);
                findsubCategories(subCategoryVoList, categories);
            }

        }
    }

    /**
     * 通过ID查询所有商品目录及其子类的id，set里存储的都是ID
     *
     */
    @Override
    public void findSubCategoryId(Integer id, Set<Integer> resultSet) {
        List<Category> categories = categoryMapper.selectAll();
        findSubCategoryId(id,resultSet,categories);
    }
    public void findSubCategoryId(Integer id, Set<Integer> resultSet, List<Category> categories) {

        for (Category category : categories) {
            if(category.getParentId().equals(id)){
                resultSet.add(category.getId());
                findSubCategoryId(category.getId(),resultSet,categories);
            }
        }
    }
}

