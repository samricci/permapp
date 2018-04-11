package com.permapp.permappapi.service;

import static com.permapp.permappapi.compose.CategoryDataBeanCompose.getDefaultCategoryDataBean;
import static java.lang.Integer.MAX_VALUE;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.permapp.permappapi.bean.CategoryDataBean;
import com.permapp.permappapi.compose.CategoryCompose;
import com.permapp.permappapi.dto.CategoryDTO;
import com.permapp.permappapi.entity.Category;
import com.permapp.permappapi.exception.EntityAlreadyExistsException;
import com.permapp.permappapi.exception.EntityNotFoundException;
import com.permapp.permappapi.exception.MissingFieldException;
import com.permapp.permappapi.repository.CategoryRepository;
import com.permapp.permappapi.service.impl.CategoryServiceImpl;
import com.permapp.permappapi.validator.MandatoryFieldsValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

  @Mock
  private CategoryRepository categoryRepository;

  @Spy
  private MandatoryFieldsValidator validator;

  @Spy
  @InjectMocks
  private CategoryService categoryService = new CategoryServiceImpl();

  @Rule
  public ErrorCollector collector = new ErrorCollector();

  @Rule
  public ExpectedException exception = ExpectedException.none();

  private Pageable PAGEABLE = PageRequest.of(0, MAX_VALUE);
  private Category CATEGORY_MOCK = CategoryCompose.getCategoryDefault().build();
  private Category CATEGORY_MOCK_2 = Category.builder().name("Categoria 1234").build();
  private CategoryDataBean CATEGORY_BEAN_MOCK = getDefaultCategoryDataBean().build();

  @Test
  public void shouldReturnAllCategories() {
    List<CategoryDTO> categoryDTOMock = asList(
        new CategoryDTO(CATEGORY_MOCK),
        new CategoryDTO(CATEGORY_MOCK),
        new CategoryDTO(CATEGORY_MOCK));

    List<Category> categoryMock = asList(CATEGORY_MOCK, CATEGORY_MOCK, CATEGORY_MOCK);
    when(categoryRepository.findAll(PAGEABLE)).thenReturn(new PageImpl<>(categoryMock));

    Page<CategoryDTO> categories = categoryService.getAll(PAGEABLE);

    verify(categoryRepository).findAll(PAGEABLE);
    verifyNoMoreInteractions(categoryRepository);
    collector.checkThat(categories.getContent(), is(categoryDTOMock));
    collector.checkThat(categories.getContent(), hasItem(new CategoryDTO(CATEGORY_MOCK)));
    collector.checkThat(categories.getContent(), hasSize(categoryDTOMock.size()));
  }

  @Test
  public void shouldReturnEmptyWhenNoCategoriesWereFound() {
    when(categoryRepository.findAll(PAGEABLE)).thenReturn(new PageImpl<>(new ArrayList<>()));

    Page<CategoryDTO> categories = categoryService.getAll(PAGEABLE);

    verify(categoryRepository).findAll(PAGEABLE);
    verifyNoMoreInteractions(categoryRepository);
    collector.checkThat(categories.getContent(), is(new ArrayList<>()));
    collector.checkThat(categories.getContent(), hasSize(0));
  }

  @Test
  public void shouldReturnACategoryByItsID() {
    Optional<Category> optionalCategory = Optional.of(CATEGORY_MOCK);

    when(categoryRepository.findById(1L)).thenReturn(optionalCategory);

    Category category = categoryService.get(1L);

    verify(categoryRepository).findById(1L);
    verifyNoMoreInteractions(categoryRepository);
    assertThat(category, is(CATEGORY_MOCK));
  }

  @Test(expected = EntityNotFoundException.class)
  public void shouldLaunchExceptionWhenCategoryNotFound() {
    Optional<Category> optionalCategory = Optional.empty();

    when(categoryRepository.findById(1L)).thenReturn(optionalCategory);

    categoryService.get(1L);
  }

  @Test(expected = EntityNotFoundException.class)
  public void shouldLaunchExceptionWhenIDIsNull() {
    Optional<Category> optionalCategory = Optional.ofNullable(null);

    when(categoryRepository.findById(null)).thenReturn(optionalCategory);

    categoryService.get(null);
  }

  @Test
  public void shouldReturnCategoryDTO() {
    doReturn(CATEGORY_MOCK).when(categoryService).get(1L);

    CategoryDTO categoryDTO = categoryService.getDTO(1L);

    verify(categoryService).get(1L);
    assertThat(categoryDTO, is(new CategoryDTO(CATEGORY_MOCK)));
  }

  @Test
  public void shouldRemoveACategory() {
    doReturn(CATEGORY_MOCK).when(categoryService).get(1L);

    CategoryDTO categoryDTO = categoryService.remove(1L);

    verify(categoryRepository).delete(CATEGORY_MOCK);
    verify(categoryService).get(1L);
    collector.checkThat(categoryDTO, is(new CategoryDTO(CATEGORY_MOCK)));
  }

  @Test
  public void shouldCreateACategory() {
    Optional<Category> optionalCategory = Optional.empty();
    when(categoryRepository.findByName("Categoria 1234")).thenReturn(optionalCategory);
    when(categoryRepository.save(any(Category.class))).thenReturn(CATEGORY_MOCK);

    categoryService.create(CATEGORY_BEAN_MOCK);

    ArgumentCaptor<Category> argument = ArgumentCaptor.forClass(Category.class);
    verify(categoryRepository).save(argument.capture());
    Category category = argument.getValue();

    verify(categoryRepository).findByName("Categoria 1234");
    collector.checkThat(category.getName(), is(CATEGORY_BEAN_MOCK.getName()));
  }

  @Test(expected = EntityAlreadyExistsException.class)
  public void shouldLaunchExceptionWhenCategoryWithSameNameAlreadyExistsOnCreate() {
    Optional<Category> optionalCategory = Optional.of(CATEGORY_MOCK_2);
    when(categoryRepository.findByName("Categoria 1234")).thenReturn(optionalCategory);

    categoryService.create(CATEGORY_BEAN_MOCK);
  }

  @Test(expected = MissingFieldException.class)
  public void shouldLaunchExceptionWhenCategoryNameIsNullOnCreate() {
    CategoryDataBean categoryBeanWithNullName = CategoryDataBean.builder().name(null).build();
    categoryService.create(categoryBeanWithNullName);
  }

  @Test
  public void shouldUpdateACategory() {
    doReturn(CATEGORY_MOCK).when(categoryService).get(1L);
    when(categoryRepository.save(any(Category.class))).thenReturn(CATEGORY_MOCK);

    categoryService.update(1L, CATEGORY_BEAN_MOCK);

    ArgumentCaptor<Category> argumentCaptor = ArgumentCaptor.forClass(Category.class);
    verify(categoryRepository).save(argumentCaptor.capture());
    Category category = argumentCaptor.getValue();

    verify(categoryRepository).findByNameExceptId("Categoria 1234", 1L);
    collector.checkThat(category.getId(), is(1L));
    collector.checkThat(category.getName(), is(CATEGORY_BEAN_MOCK.getName()));
  }

  @Test(expected = EntityAlreadyExistsException.class)
  public void shouldLaunchExceptionWhenCategoryWithSameNameAlreadyExistsOnUpdate() {
    Optional<Category> optionalCategory = Optional.of(CATEGORY_MOCK_2);
    when(categoryRepository.findByNameExceptId("Categoria 1234", 1L)).thenReturn(optionalCategory);

    categoryService.update(1L, CATEGORY_BEAN_MOCK);
  }

  @Test(expected = MissingFieldException.class)
  public void shouldLaunchExceptionWhenCategoryNameIsNullOnUpdate() {
    CategoryDataBean categoryBeanWithNullName = CategoryDataBean.builder().name(null).build();
    categoryService.update(1L, categoryBeanWithNullName);
  }
}
