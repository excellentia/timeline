<%-- TLD inclusion --%>
<%@ include file="inc/includes.inc.jsp"%>
<div class="tableSpacing">
	<c:choose>
		<c:when test="${((detailReply != null) && (detailReply.weeklyMetrics != null) && (detailReply.weeklyMetrics.size() > 0))}">
			<table style="width: 100%;" id="detailTable">
				<colgroup>
					<col style="width: 16%" />
					<col style="width: 5%" />	
					<col style="width: 5%" />
					<col style="width: 5%" />
					<col style="width: 5%" />
					<col style="width: 5%" />
					<col style="width: 5%" />
					<col style="width: 5%" />
					<col style="width: 5%" />
					<col style="width: 5%" />
					<col style="width: 5%" />
					<col style="width: 5%" />
					<col style="width: 5%" />
					<col style="width: 5%" />
					<col style="width: 5%" />
					<col style="width: 5%" />
					<col style="width: 5%" />
					<col style="width: 2%" />
					<col style="width: 2%" />
				</colgroup>
				<c:set var ="basicData" value="${detailReply.basicData}"/>
				<c:set var ="projectId" value="${basicData.projectData.code}"/>
				<thead>
					<tr align="center">
						<th class="detailTabArea">Detailed Metrics : ${basicData.projectData.value}</th>
						<th class="detailTabArea" colspan="6" style="border-left: 1px solid white" title="Project Start Date">Start Date : <fmt:formatDate value="${basicData.startDate}" pattern="dd MMM yyyy"/></th>
						<th class="detailTabArea" colspan="6" style="border-left: 1px solid white" title="Project End Date">End Date : <fmt:formatDate value="${basicData.endDate}" pattern="dd MMM yyyy"/></th>
						<th class="detailTabArea" colspan="4" style="border-left: 1px solid white" title="Project Budget At Completion">BAC : <fmt:formatNumber minFractionDigits="1" maxFractionDigits="1" value="${basicData.budgetAtCompletion}"/></th>
						<th class="whiteBorderHeader" colspan="2"></th>										
					</tr>
					<tr>
						<th rowspan="2">Period</th>
						<th colspan="6" title="Metrics Entered">Weekly Metrics</th>
						<th colspan="6" class="reportTotal" title="Cumulative Metrics Per Week">Weekly Cumulative Metrics</th>
						<th colspan="4" class="calculatedMetrics" title="Calculated Metrics Per Week">Weekly Calculated Metrics</th>
						<th colspan="2" rowspan="2">&nbsp;</th>
					</tr>
					<tr>
						<th title="Weekly Planned Value">PV</th>
						<th title="Weekly Earned Value">EV</th>
						<th title="Weekly Actual Cost">AC</th>
						<th title="Weekly Actuals To Date">ATD</th>
						<th title="Weekly Software Project Effort">SPE</th>
						<th title="Weekly  Defects">Bugs</th>
						<th title="Cumulative Planned Value" class="reportTotal">&Sigma; PV</th>
						<th title="Cumulative Earned Value" class="reportTotal">&Sigma; EV</th>
						<th title="Cumulative Actual Cost" class="reportTotal">&Sigma; AC</th>
						<th title="Cumulative Actuals To Date" class="reportTotal">&Sigma; ATD</th>
						<th title="Cumulative Software Project Effort" class="reportTotal">&Sigma; SPE</th>
						<th title="Cumulative Defects" class="reportTotal">&Sigma; Bugs</th>
						<th title="Expected To Complete" class="calculatedMetrics">ETC</th>
						<th title="CostProject Index" class="calculatedMetrics">CPI</th>
						<th title="Schedule Project Index" class="calculatedMetrics">SPI</th>
						<th title="Ratio : Defect / SPE" class="calculatedMetrics">Ratio</th>										
					</tr>
				</thead>
				<tbody class="reportBody">
					<c:set var="cumlPV" value="0"/>
					<c:set var="cumlEV" value="0"/>
					<c:set var="cumlAC" value="0"/>
					<c:set var="cumlATD" value="0"/>
					<c:set var="cumlSPE" value="0"/>
					<c:set var="cumlDefects" value="0"/>
					<c:set var="bac" value="${basicData.budgetAtCompletion}"/>
					<c:set var="prevMetricRowId" value=""/>
					
					<c:forEach var="detailMetric" items="${detailReply.weeklyMetrics}">
						<c:set var="basicMetrics" value="${detailMetric.weeklyMetrics}"/>
						<c:set var ="metricId" value="${detailMetric.metricId}"/>
						<c:set var ="metricRowId" value="metricRow_${metricId}"/>
						<tr id="${metricRowId}">
							<c:set var="lastSavedTS"><fmt:formatDate value="${detailMetric.lastSaved}" pattern="dd MMM yyyy"/></c:set>
							<td title="Last Saved : ${lastSavedTS}">${detailMetric.metricWeek}</td>
							<td id="pv_${metricId}">
								<fmt:formatNumber minFractionDigits="1" maxFractionDigits="1" value="${basicMetrics.plannedValue}"/>
								<c:set var="cumlPV" value="${cumlPV + basicMetrics.plannedValue}"/>
							</td>
							<td id="ev_${metricId}">
								<fmt:formatNumber minFractionDigits="1" maxFractionDigits="1" value="${basicMetrics.earnedValue}"/>
								<c:set var="cumlEV" value="${cumlEV + basicMetrics.earnedValue}"/>
							</td>
							<td id="ac_${metricId}">
								<fmt:formatNumber minFractionDigits="1" maxFractionDigits="1" value="${basicMetrics.actualCost}"/>
								<c:set var="cumlAC" value="${cumlAC + basicMetrics.actualCost}"/>
							</td>
							<td id="atd_${metricId}">
								<fmt:formatNumber minFractionDigits="1" maxFractionDigits="1" value="${basicMetrics.actualsToDate}"/>
								<c:set var="cumlATD" value="${cumlATD + basicMetrics.actualsToDate}"/>
							</td>
							<td id="spe_${metricId}">
								<fmt:formatNumber minFractionDigits="1" maxFractionDigits="1" value="${basicMetrics.softwareProgrammingEffort}"/>
								<c:set var="cumlSPE" value="${cumlSPE + basicMetrics.softwareProgrammingEffort}"/>
							</td>
							<td id="defects_${metricId}">
								${basicMetrics.defects}
								<c:set var="cumlDefects" value="${cumlDefects + basicMetrics.defects}"/>
							</td>
							<td class="reportTotal" id="cumlPV_${metricId}"><fmt:formatNumber minFractionDigits="1" maxFractionDigits="1" value="${cumlPV}"/></td>
							<td class="reportTotal" id="cumlEV_${metricId}"><fmt:formatNumber minFractionDigits="1" maxFractionDigits="1" value="${cumlEV}"/></td>
							<td class="reportTotal" id="cumlAC_${metricId}"><fmt:formatNumber minFractionDigits="1" maxFractionDigits="1" value="${cumlAC}"/></td>
							<td class="reportTotal" id="cumlATD_${metricId}"><fmt:formatNumber minFractionDigits="1" maxFractionDigits="1" value="${cumlATD}"/></td>
							<td class="reportTotal" id="cumlSPE_${metricId}"><fmt:formatNumber minFractionDigits="1" maxFractionDigits="1" value="${cumlSPE}"/></td>
							<td class="reportTotal" id="cumlDefects_${metricId}">${cumlDefects}</td>
							<td class="calculatedMetrics" id="etc_${metricId}"><fmt:formatNumber minFractionDigits="1" maxFractionDigits="1" value="${bac - basicMetrics.earnedValue}"/></td>
							<td class="calculatedMetrics" id="cpi_${metricId}"><fmt:formatNumber minFractionDigits="3" maxFractionDigits="3" value="${basicMetrics.earnedValue / basicMetrics.actualCost}"/></td>
							<td class="calculatedMetrics" id="spi_${metricId}"><fmt:formatNumber minFractionDigits="3" maxFractionDigits="3" value="${basicMetrics.earnedValue / basicMetrics.plannedValue}"/></td>
							<td class="calculatedMetrics" id="ratio_${metricId}"><fmt:formatNumber minFractionDigits="3" maxFractionDigits="3" value="${basicMetrics.defects / basicMetrics.softwareProgrammingEffort}"/></td>
							<td align="center">
								<img alt="Edit" align="middle" class="icon" title="Edit" src="${editIconPath}" onclick="editMetricDetails('${metricRowId}', '${prevMetricRowId}' ,${projectId}, ${metricId}, ${bac})"/>
							</td>
							<td align="center">
								<img alt="Delete" align="middle" class="icon" title="Delete This Row" src="${deleteIconPath}" onclick="deleteMetricDetails('${metricRowId}', ${metricId})"/>
							</td>
							<c:set var="prevMetricRowId" value="${metricRowId}"/>
						</tr>
						<c:remove var="basicMetrics" />
					</c:forEach>
				</tbody>
				<c:remove var ="basicData"/>
			</table>
		</c:when>
		<c:otherwise>
			<p class="error" style="padding: 0; margin: 0">No Metric Entries Found</p>
		</c:otherwise>
	</c:choose>
</div>
