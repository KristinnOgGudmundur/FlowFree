package com.example.FlowFree.objects;

import android.content.Context;
import android.graphics.*;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Board extends View {

    //Grid info
    private int NUM_CELLS = 6;
    private int m_cellWidth;
    private int m_cellHeight;
    private Rect m_rect = new Rect();
    private Paint m_paintGrid  = new Paint();

    private final int[] COLORS = {Color.YELLOW, Color.RED, Color.GREEN, Color.argb(255, 100, 100, 255),
                                  Color.CYAN, Color.MAGENTA};

	private boolean colorBlindMode = true;
	private Paint m_paintColorNumbers = new Paint();

    private LineInfo m_lineInfo;
    private Path m_path = new Path();
    private float m_radius = 30;

    private Cellpath m_currentCellPath = null;
	private Line m_currentLine = null;

	private Vibrator m_vibrator;
	private boolean wasComplete = false;

    //region x&y to col&row
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
    //endregion

    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);
        m_paintGrid.setStyle( Paint.Style.STROKE );
        m_paintGrid.setColor( Color.GRAY );
		m_paintGrid.setStrokeWidth(8);

		m_paintColorNumbers.setStyle(Paint.Style.FILL);
		m_paintColorNumbers.setStrokeWidth(4);
		m_paintColorNumbers.setColor(Color.BLACK);
		m_paintColorNumbers.setTextSize(m_radius * 1.75f);
		m_paintColorNumbers.setTextAlign(Paint.Align.CENTER);

		m_vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);

    }

    public void setupBoard(Puzzle myPuzzle){

        ArrayList<Line> theLines = new ArrayList<Line>();
        NUM_CELLS = myPuzzle.getGridSize();
        int color = 0;
        for(Line l : myPuzzle.getLines())
        {
            theLines.add(new Line(new Coordinate(l.getStart().getCol(), l.getStart().getRow()),     //start Coordinate
                                  new Coordinate(l.getEnd().getCol(), l.getEnd().getRow()),         //end Coordinate
                                  COLORS[color],                                                    //color int
                                  color));                                                          //colorblind int
            color++;
        }

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

		for(Line l : m_lineInfo.getAllLines()) {
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
            l.getPaint().setStyle(Paint.Style.STROKE);
			canvas.drawPath(m_path, l.getPaint());

            l.getPaint().setStyle(Paint.Style.FILL);
            //draw starting coordinates
            canvas.drawCircle(colToX(l.getStart().getCol()) + m_cellWidth / 2,  // float x
                              rowToY(l.getStart().getRow()) + m_cellWidth / 2,  // float y
                              m_radius,                                         // float radius
                              l.getPaint());                                    // Paint Color

            //colorblind numbers
			canvas.drawText(l.getColorBlindString(),
					colToX(l.getStart().getCol()) + m_cellWidth / 2,
					rowToY(l.getStart().getRow()) + m_cellWidth / 2 + m_radius / 2,
					m_paintColorNumbers);

            //draw end coordinates
            canvas.drawCircle(colToX(l.getEnd().getCol()) + m_cellWidth / 2,    // float x
                              rowToY(l.getEnd().getRow()) + m_cellWidth / 2,    // float y
                              m_radius,                                         // float radius
                              l.getPaint());                                    // Paint Color

            //colorblind numbers
			canvas.drawText(l.getColorBlindString(),
					colToX(l.getEnd().getCol()) + m_cellWidth / 2,
					rowToY(l.getEnd().getRow()) + m_cellWidth / 2 + m_radius / 2,
					m_paintColorNumbers);
		}
    }

    @Override
    public boolean onTouchEvent( MotionEvent event ) {

        int x = (int) event.getX();
        int y = (int) event.getY();
        int c = xToCol( x );
        int r = yToRow( y );
		Coordinate theCoordinate = new Coordinate(c, r);

        if ( c >= NUM_CELLS || r >= NUM_CELLS ) {
            return true;
        }

        if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
			//TODO: This will need to be refactored and encapsulated
			wasComplete = false;
			m_currentCellPath = m_lineInfo.getCellPath(theCoordinate);
            if(m_currentCellPath == null){
				return false;
			}
			m_currentLine = m_lineInfo.getLine(m_currentCellPath);
			Coordinate currentCoordinate = new Coordinate(c, r);
			if(m_currentLine.isStartingPoint(currentCoordinate)) {
				m_currentCellPath.reset();
				m_currentCellPath.append(currentCoordinate);
			}
			else{
				m_currentCellPath.setEndAt(currentCoordinate);
				invalidate();
			}
        }
        else if ( event.getAction() == MotionEvent.ACTION_MOVE ) {
            if ( !m_currentCellPath.isEmpty() ) {
				m_lineInfo.addToCellPath(m_currentCellPath, theCoordinate);
				if(!wasComplete && m_currentLine.complete() && !m_lineInfo.allComplete()){
					m_vibrator.vibrate(100);
					wasComplete = true;
				}
				invalidate();
            }
        }
		else if(event.getAction() == MotionEvent.ACTION_UP){
			if(m_lineInfo.allComplete()){
				//Puzzle complete
				Toast.makeText(this.getContext(), "Puzzle complete", Toast.LENGTH_LONG).show();
				m_vibrator.vibrate(200);
			}
		}
        return true;
    }

	public void reset(){
		m_lineInfo.resetAll();
		invalidate();
	}
}
