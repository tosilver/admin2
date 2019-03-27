package co.b4pay.admin.common.system.entity;


import co.b4pay.admin.entity.base.BaseEntity;


/**
 * 资源表
 *
 * @author cc
 */
public class Resource extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name;                                 // 资源名称
    private ResourceType type = ResourceType.MENU; // 资源类型
    private String url;                                  // 资源路径
    private String permission;                           // 权限字符串
    private String parentId;                             // 父编号
    private String parentIds;                            // 父编号列表
    private Integer sort;                                 // 顺序号
    private Boolean available = Boolean.FALSE;

    private Resource parent;

    public enum ResourceType {
        MENU("菜单"), BUTTON("按钮");

        private final String text;

        ResourceType(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    public Resource() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public boolean isRootNode() {
        return "0".equals(parentId);
    }

    public String makeSelfAsParentIds() {
        return getParentIds() + "/" + getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Resource resource = (Resource) o;

        if (id != null ? !id.equals(resource.id) : resource.id != null)
            return false;

        return true;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Resource getParent() {
        return parent;
    }

    public void setParent(Resource parent) {
        this.parent = parent;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return name;
    }
}
