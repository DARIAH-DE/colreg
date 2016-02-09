<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<nav class="nav-form-controls">
	<h3><s:message code="~eu.dariah.de.colreg.titles.properties" /></h3>
	<ul class="nav">
		<li><a href="#"><s:message code="~eu.dariah.de.colreg.model.agent.groups.mandatory_description" /></a>
			<ul class="nav">
				<s:bind path="agent.agentTypeId">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#agentTypeId"><s:message code="~eu.dariah.de.colreg.model.agent.type" /></a></li>
				</s:bind>
				<s:bind path="agent.name">
					<li class="agent-nonnatural-only ${status.error ? ' has-error' : ''}" <c:if test="${agentIsNatural}"> style="display: none;"</c:if>><a href="#name"><s:message code="~eu.dariah.de.colreg.model.agent.name" /></a></li>
					<li class="agent-natural-only ${status.error ? ' has-error' : ''}" <c:if test="${!agentIsNatural}"> style="display: none;"</c:if>><a href="#name"><s:message code="~eu.dariah.de.colreg.model.agent.last_name" /></a></li>
				</s:bind>
				<s:bind path="agent.foreName">
					<li class="agent-natural-only ${status.error ? ' has-error' : ''}" <c:if test="${!agentIsNatural}"> style="display: none;"</c:if>><a  href="#foreName"><s:message code="~eu.dariah.de.colreg.model.agent.first_name" /></a></li>
				</s:bind>
			</ul>
		</li>
		<li><a href="#"><s:message code="~eu.dariah.de.colreg.model.agent.groups.extended_description" /></a>
			<ul class="nav">
				<s:bind path="agent.addresses*">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#tbl-agent-addresses"><s:message code="~eu.dariah.de.colreg.model.agent.addresses" /></a></li>
				</s:bind>
				<s:bind path="agent.eMail">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#eMail"><s:message code="~eu.dariah.de.colreg.model.agent.email" /></a></li>
				</s:bind>
				<s:bind path="agent.webPage">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#webPage"><s:message code="~eu.dariah.de.colreg.model.agent.webpage" /></a></li>
				</s:bind>
				<s:bind path="agent.phone">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#phone"><s:message code="~eu.dariah.de.colreg.model.agent.phone" /></a></li>
				</s:bind>
			</ul>
		</li>
		<li><a href="#"><s:message code="~eu.dariah.de.colreg.model.agent.groups.contextual" /></a>
			<ul class="nav">
				<s:bind path="agent.parentAgentId">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#parentAgentIdSelector"><s:message code="~eu.dariah.de.colreg.model.agent.parent_agent" /></a></li>
				</s:bind>
				<li><a href="#child-agents"><s:message code="~eu.dariah.de.colreg.model.agent.child_agents" /></a></li>
				<li><a href="#associated-collections"><s:message code="~eu.dariah.de.colreg.model.agent.associated_collections" /></a></li>
				<s:bind path="agent.providedIdentifier*">
					<li ${status.error ? 'class="has-error"' : ' '}><a href="#lst-agent-provided-identifiers"><s:message code="~eu.dariah.de.colreg.model.agent.provided_identifiers" /></a></li>
				</s:bind>
			</ul>
		</li>
		<c:if test="${!isNew}">
			<li><a href="#"><s:message code="~eu.dariah.de.colreg.model.agent.groups.identification_and_administration" /></a>
				<ul class="nav">
					<li><a href="#agent-identifier"><s:message code="~eu.dariah.de.colreg.model.agent.agent_identifier" /></a></li>
					<li><a href="#version-identifier"><s:message code="~eu.dariah.de.colreg.model.agent.version_identifier" /></a></li>
					<li><a href="#current-version"><s:message code="~eu.dariah.de.colreg.model.agent.current_version" /></a></li>
					<li><a href="#initially-created"><s:message code="~eu.dariah.de.colreg.model.agent.created" /></a></li>
					<c:if test="${!isDeleted}">
						<li><a href="#agent-administration"><s:message code="~eu.dariah.de.colreg.model.agent.groups.administration" /></a></li>
					</c:if>
				</ul>
			</li>		
		</c:if>
	</ul>
</nav>