<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<c:choose>
	<c:when test="${editMode}">
		<div id="${currRightsName}" class="rights-container col-sm-9">
			<div class="col-sm-1">
				<label class="radio-inline pull-right">
				  <input type="radio" <c:if test="${!currRightsLicenseId}">checked="checked" </c:if>name="${currRightsName}-group" value="freetext">
				</label>
			</div>
			<div class="col-sm-11">
				<input id="${currRightsName}-group-freetext" name="<c:if test="${!currRightsLicenseId}">${currRightsName}</c:if>" value="<c:if test="${!currRightsLicenseId}">${currRightsValue}</c:if>" class="form-control form-control-subcontrol" <c:if test="${currRightsLicenseId}">disabled </c:if>/>
			</div>
			
			<div class="col-sm-1" style="margin-top: 10px;">
				<label class="radio-inline pull-right">
				  <input type="radio" <c:if test="${currRightsLicenseId}">checked="checked" </c:if>name="${currRightsName}-group" value="license">
				</label>
			</div>
			<div class="col-sm-11" style="margin-top: 10px;">
			<select id="${currRightsName}-group-license" name="<c:if test="${currRightsLicenseId}">${currRightsName}</c:if>" class="form-control form-control-subcontrol select-license" <c:if test="${!currRightsLicenseId}">disabled </c:if> >
				<c:forEach items="${licenseGroups}" var="licenseGroup">
					<c:set var="groupLabel" value="${licenseGroup.labels['en']}"></c:set>
					<c:forEach items="${licenseGroup.labels}" var="label">
						<c:if test="${label.key==locale}">
					    	<c:set var="groupLabel" value="${label.value}"></c:set>
					    </c:if>
					</c:forEach>
					<optgroup label="${groupLabel}">
					<c:forEach items="${licenseGroup.licenses}" var="license">
						<c:set var="selected"></c:set>
						<c:if test="${currRightsValue==license.id}"><c:set var="selected">selected="selected"</c:set></c:if>
						<option value="${license.id}" ${selected}>${license.identifier} (${license.label})</option>
					</c:forEach>	
				</optgroup>
				
				</c:forEach>
			</select>
		</div>
		</div>
		
	</c:when>
	<c:otherwise>
		<div id="${currRightsName}" class="col-sm-9">
			<label class="control-label">
				<a href="javascript:void(0)">
					<c:choose>
						<c:when test="${currRightsLicenseId}">
							<c:forEach items="${licenseGroups}" var="licenseGroup">
								<c:forEach items="${licenseGroup.licenses}" var="license">
									<c:if test="${currRightsValue==license.id}">
										${license.identifier} (${license.label})
									</c:if>
								</c:forEach>
							</c:forEach>
						</c:when>
						<c:otherwise>
							${currRightsValue}
						</c:otherwise>
					</c:choose>
				</a> 
			</label>
		</div>
	</c:otherwise>
</c:choose>