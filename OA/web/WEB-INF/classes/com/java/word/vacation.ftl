<?xml version="1.0"?>
<?mso-application progid="Excel.Sheet"?>
<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:o="urn:schemas-microsoft-com:office:office"
 xmlns:x="urn:schemas-microsoft-com:office:excel"
 xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:html="http://www.w3.org/TR/REC-html40">
 <DocumentProperties xmlns="urn:schemas-microsoft-com:office:office">
  <LastPrinted>2017-04-28T07:18:29Z</LastPrinted>
  <Created>1996-12-17T01:32:42Z</Created>
  <LastSaved>2017-04-28T03:03:51Z</LastSaved>
  <Version>11.9999</Version>
 </DocumentProperties>
 <OfficeDocumentSettings xmlns="urn:schemas-microsoft-com:office:office">
  <RemovePersonalInformation/>
 </OfficeDocumentSettings>
 <ExcelWorkbook xmlns="urn:schemas-microsoft-com:office:excel">
  <WindowHeight>4530</WindowHeight>
  <WindowWidth>8505</WindowWidth>
  <WindowTopX>480</WindowTopX>
  <WindowTopY>120</WindowTopY>
  <AcceptLabelsInFormulas/>
  <ProtectStructure>False</ProtectStructure>
  <ProtectWindows>False</ProtectWindows>
 </ExcelWorkbook>
 <Styles>
  <Style ss:ID="Default" ss:Name="Normal">
   <Alignment ss:Vertical="Bottom"/>
   <Borders/>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="12"/>
   <Interior/>
   <NumberFormat/>
   <Protection/>
  </Style>
  <Style ss:ID="m94982392">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="22"/>
  </Style>
  <Style ss:ID="s21">
   <Alignment ss:Horizontal="Center" ss:Vertical="Bottom"/>
  </Style>
  <Style ss:ID="s29">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <Interior ss:Color="#99CCFF" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="s30">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <NumberFormat ss:Format="@"/>
  </Style>
 </Styles>
 <Worksheet ss:Name="Sheet1">
  <Table ss:ExpandedColumnCount="8" ss:ExpandedRowCount="4000" x:FullColumns="1"
   x:FullRows="1" ss:DefaultColumnWidth="54" ss:DefaultRowHeight="14.25">
   <Column ss:StyleID="s21" ss:AutoFitWidth="0" ss:Width="27"/>
   <Column ss:StyleID="s21" ss:AutoFitWidth="0" ss:Width="118.5"/>
   <Column ss:StyleID="s21" ss:AutoFitWidth="0" ss:Width="62.25"/>
   <Column ss:StyleID="s21" ss:AutoFitWidth="0" ss:Width="48.75"/>
   <Column ss:StyleID="s21" ss:AutoFitWidth="0" ss:Width="51.75"/>
   <Column ss:StyleID="s21" ss:AutoFitWidth="0" ss:Width="68.25"/>
   <Column ss:StyleID="s21" ss:AutoFitWidth="0" ss:Width="66.75"/>
   <Column ss:StyleID="s21" ss:AutoFitWidth="0" ss:Width="42"/>
   <Row ss:AutoFitHeight="0" ss:Height="34.5">
    <Cell ss:MergeAcross="7" ss:StyleID="m94982392"><Data ss:Type="String">假 期 统 计</Data></Cell>
   </Row>
   <Row ss:AutoFitHeight="0">
    <Cell ss:StyleID="s29"><Data ss:Type="String">序号</Data></Cell>
    <Cell ss:StyleID="s29"><Data ss:Type="String">创建时间</Data></Cell>
    <Cell ss:StyleID="s29"><Data ss:Type="String">部门</Data></Cell>
    <Cell ss:StyleID="s29"><Data ss:Type="String">员工</Data></Cell>
    <Cell ss:StyleID="s29"><Data ss:Type="String">假期类型</Data></Cell>
    <Cell ss:StyleID="s29"><Data ss:Type="String">起始日期</Data></Cell>
    <Cell ss:StyleID="s29"><Data ss:Type="String">结束日期</Data></Cell>
    <Cell ss:StyleID="s29"><Data ss:Type="String">状态</Data></Cell>
   </Row>
   <#if list?? && (list?size > 0)>
   		<#list list as obj>
		   <Row ss:AutoFitHeight="0">
		    <Cell ss:StyleID="s30"><Data ss:Type="String">${obj_index+1}</Data></Cell>
		    <Cell ss:StyleID="s30"><Data ss:Type="String"><#if obj.time??>${obj.time}</#if></Data></Cell>
		    <Cell ss:StyleID="s30"><Data ss:Type="String"><#if obj.dept??>${obj.dept}</#if></Data></Cell>
		    <Cell ss:StyleID="s30"><Data ss:Type="String"><#if obj.user??>${obj.user}</#if></Data></Cell>
		    <Cell ss:StyleID="s30"><Data ss:Type="String"><#if obj.type??><#if obj.type=="01">出差<#elseif obj.type=="02">事假<#elseif obj.type=="03">病假<#elseif obj.type=="04">产假<#elseif obj.type=="05">年假<#elseif obj.type=="06">婚假<#elseif obj.type=="07">丧假<#else></#if></#if></Data></Cell>
		    <Cell ss:StyleID="s30"><Data ss:Type="String"><#if obj.startDate??>${obj.startDate}</#if></Data></Cell>
		    <Cell ss:StyleID="s30"><Data ss:Type="String"><#if obj.endDate??>${obj.endDate}</#if></Data></Cell>
		    <Cell ss:StyleID="s30"><Data ss:Type="String"><#if obj.subflag??><#if obj.subflag=="02">已办结<#else>办理中</#if></#if></Data></Cell>
		   </Row>
   		</#list>
   <#else>
	   <Row ss:AutoFitHeight="0">
	    <Cell ss:StyleID="s30"><Data ss:Type="String"></Data></Cell>
	    <Cell ss:StyleID="s30"><Data ss:Type="String"></Data></Cell>
	    <Cell ss:StyleID="s30"><Data ss:Type="String"></Data></Cell>
	    <Cell ss:StyleID="s30"><Data ss:Type="String"></Data></Cell>
	    <Cell ss:StyleID="s30"><Data ss:Type="String"></Data></Cell>
	    <Cell ss:StyleID="s30"><Data ss:Type="String"></Data></Cell>
	    <Cell ss:StyleID="s30"><Data ss:Type="String"></Data></Cell>
	    <Cell ss:StyleID="s30"><Data ss:Type="String"></Data></Cell>
	   </Row>
   </#if>
  </Table>
  <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
   <Unsynced/>
   <Print>
    <ValidPrinterInfo/>
    <PaperSizeIndex>9</PaperSizeIndex>
    <HorizontalResolution>600</HorizontalResolution>
    <VerticalResolution>0</VerticalResolution>
   </Print>
   <Selected/>
   <Panes>
    <Pane>
     <Number>3</Number>
     <RangeSelection>R1C1:R1C8</RangeSelection>
    </Pane>
   </Panes>
   <ProtectObjects>False</ProtectObjects>
   <ProtectScenarios>False</ProtectScenarios>
  </WorksheetOptions>
 </Worksheet>
 <Worksheet ss:Name="Sheet2">
  <Table ss:ExpandedColumnCount="0" ss:ExpandedRowCount="0" x:FullColumns="1"
   x:FullRows="1" ss:DefaultColumnWidth="54" ss:DefaultRowHeight="14.25"/>
  <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
   <Unsynced/>
   <ProtectObjects>False</ProtectObjects>
   <ProtectScenarios>False</ProtectScenarios>
  </WorksheetOptions>
 </Worksheet>
 <Worksheet ss:Name="Sheet3">
  <Table ss:ExpandedColumnCount="0" ss:ExpandedRowCount="0" x:FullColumns="1"
   x:FullRows="1" ss:DefaultColumnWidth="54" ss:DefaultRowHeight="14.25"/>
  <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
   <Unsynced/>
   <ProtectObjects>False</ProtectObjects>
   <ProtectScenarios>False</ProtectScenarios>
  </WorksheetOptions>
 </Worksheet>
</Workbook>
