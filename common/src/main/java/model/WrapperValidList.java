package model;

import com.google.common.collect.ForwardingList;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

public class WrapperValidList<T> extends ForwardingList<T> {

  private List<@Valid T> list;

  public WrapperValidList() {
    this(new ArrayList<>());
  }

  public WrapperValidList(List<@Valid T> list) {
    this.list = list;
  }

  @Override
  protected List<T> delegate() {
    return list;
  }

  /** Exposed for the {@link javax.validation.Validator} to access the list path */
  public List<T> getList() {
    return list;
  }
}