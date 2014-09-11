package com.example.FlowFree;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Board extends View {

    private final int NUM_CELLS = 5;
    private int m_cellWidth;
    private int m_cellHeight;

    private Rect m_rect = new Rect();
    private Paint m_paintGrid  = new Paint();
    private Paint m_paintPath  = new Paint();
	private LineInfo m_lineInfo;
    private Path m_path = new Path();

    private Cellpath m_currentCellPath = null;

    private int xToCol( int x ) {
        return (x - getPaddingLeft()) / m_cellWidth;
    }

    private int yToRow( int y ) {
        return (y - getPaddingTop()) / m_cellHeight;
    }

    private int colToX( int col ) {
        return col * m_cellWidth + getPaddingLeft() ;
    }

    private int rowToY( int row ) {
        return row * m_cellHeight + getPaddingTop() ;
    }

    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);

        m_paintGrid.setStyle( Paint.Style.STROKE );
        m_paintGrid.setColor( Color.GRAY );
		m_paintGrid.setStrokeWidth(8);

        m_paintPath.setStyle( Paint.Style.STROKE );
        m_paintPath.setColor(Color.GREEN);
        m_paintPath.setStrokeWidth(32);
        m_paintPath.setStrokeCap( Paint.Cap.ROUND );
        m_paintPath.setStrokeJoin( Paint.Join.ROUND );
        m_paintPath.setAntiAlias( true );

		setupBoard();
    }

	private void setupBoard(){
		ArrayList<Line> theLines = new ArrayList<Line>();
		theLines.add(new Line(new Coordinate(0,0), new Coordinate(NUM_CELLS - 1, NUM_CELLS - 1)));
		theLines.add(new Line(new Coordinate(1,1), new Coordinate(2, 2)));
		m_lineInfo = new LineInfo(theLines, NUM_CELLS);
	}

    @Override
    protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec ) {
        super.onMeasure( widthMeasureSpec, heightMeasureSpec );
        int width  = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        int size = Math.min(width, height);
        setMeasuredDimension( size + getPaddingLeft() + getPaddingRight(),
                size + getPaddingTop() + getPaddingBottom() );
    }

    @Override
    protected void onSizeChanged( int xNew, int yNew, int xOld, int yOld ) {
        int sw = Math.max(1, (int) m_paintGrid.getStrokeWidth());
        m_cellWidth  = (xNew - getPaddingLeft() - getPaddingRight() - sw) / NUM_CELLS;
        m_cellHeight = (yNew - getPaddingTop() - getPaddingBottom() - sw) / NUM_CELLS;
    }

    @Override
    protected void onDraw( Canvas canvas ) {
		//Draw the grid
        for ( int r=0; r<NUM_CELLS; ++r ) {
            for (int c = 0; c<NUM_CELLS; ++c) {
                int x = colToX( c );
                int y = rowToY( r );
                m_rect.set(x, y, x + m_cellWidth, y + m_cellHeight);
                canvas.drawRect( m_rect, m_paintGrid );
            }
        }

		for(Line l : m_lineInfo.allLines) {
			//Reset the pencil
			m_path.reset();
			Cellpath thePath = l.getPath();
			if (!thePath.isEmpty()) {
				List<Coordinate> colist = thePath.getCoordinates();
				Coordinate co = colist.get(0);
				m_path.moveTo(colToX(co.getCol()) + m_cellWidth / 2,
						rowToY(co.getRow()) + m_cellHeight / 2);
				for (int i = 1; i < colist.size(); ++i) {
					co = colist.get(i);
					m_path.lineTo(colToX(co.getCol()) + m_cellWidth / 2,
							rowToY(co.getRow()) + m_cellHeight / 2);
				}
			}
			canvas.drawPath(m_path, m_paintPath);
		}
    }

    @Override
    public boolean onTouchEvent( MotionEvent event ) {

        int x = (int) event.getX();         // NOTE: event.getHistorical... might be needed.
        int y = (int) event.getY();
        int c = xToCol( x );
        int r = yToRow( y );
		Coordinate theCoordinate = new Coordinate(c, r);

        if ( c >= NUM_CELLS || r >= NUM_CELLS ) {
            return true;
        }

        if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
			m_currentCellPath = m_lineInfo.getCellPath(theCoordinate);
            if(m_currentCellPath == null){
				return false;
			}
            m_currentCellPath.reset();
            m_currentCellPath.append( new Coordinate(c,r) );
        }
        else if ( event.getAction() == MotionEvent.ACTION_MOVE ) {
            if ( !m_currentCellPath.isEmpty() ) {
				m_lineInfo.addToCellPath(m_currentCellPath, theCoordinate);
				invalidate();
            }
        }
        return true;
    }

	public void setColor(int color){
		m_paintPath.setColor(color);
		invalidate();
	}
}
